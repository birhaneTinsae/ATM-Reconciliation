/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.utils;

import java.util.List;

/**
 *
 * @author btinsae
 * @param <T>
 */
public interface Common<T> {
   T store(T t);
   T show(int id);
   T update(T t);
   boolean delete(int id);

    /**
     *
     * @return
     */
    List<T> getAll();
    
            
}
