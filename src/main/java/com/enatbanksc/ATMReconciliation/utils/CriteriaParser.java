package com.enatbanksc.ATMReconciliation.utils;


import com.google.common.base.Joiner;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CriteriaParser {

    private static Map<String, Operator> ops;

    private static Pattern SpecCriteriaRegex = Pattern.compile("^(\\w+?)(" + Joiner.on("|")
            .join(SearchOperation.SIMPLE_OPERATION_SET) + ")(\\p{Punct}?)(\\w+?)(\\p{Punct}?)$");

    private enum Operator {

        OR(1), AND(2);
        final int precedence;

        Operator(int p) {
            precedence = p;
        }
    }

    static {

        ops = Map.of("AND", Operator.AND, "OR", Operator.OR, "or", Operator.OR, "and", Operator.AND);
    }

    private static boolean isHigherPrecedenceOperator(String currOp, String prevOp) {
        return (ops.containsKey(prevOp) && ops.get(prevOp).precedence >= ops.get(currOp).precedence);
    }

    public Deque<?> parse(String searchParam) {

        Deque<Object> output = new LinkedList<>();
        Deque<String> stack = new LinkedList<>();

        Arrays.stream(searchParam.split("\\s+")).forEach(token -> {
            if (ops.containsKey(token)) {
                while (!stack.isEmpty() && isHigherPrecedenceOperator(token, stack.peek()))
                    output.push(stack.pop()
                            .equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
                stack.push(token.equalsIgnoreCase(SearchOperation.OR_OPERATOR) ? SearchOperation.OR_OPERATOR : SearchOperation.AND_OPERATOR);
            } else if (token.equals(SearchOperation.LEFT_PARANTHESIS)) {
                stack.push(SearchOperation.LEFT_PARANTHESIS);
            } else if (token.equals(SearchOperation.RIGHT_PARANTHESIS)) {
                while (!stack.peek()
                        .equals(SearchOperation.LEFT_PARANTHESIS))
                    output.push(stack.pop());
                stack.pop();
            } else {

                Matcher matcher = SpecCriteriaRegex.matcher(token);
                while (matcher.find()) {
                    output.push(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4), matcher.group(5)));
                }
            }
        });

        while (!stack.isEmpty())
            output.push(stack.pop());

        return output;
    }
}
