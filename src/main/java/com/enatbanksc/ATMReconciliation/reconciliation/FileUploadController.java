/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;


import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransaction;
import com.enatbanksc.ATMReconciliation.local.etswitch.ETSTransactionService;
import com.enatbanksc.ATMReconciliation.storage.StorageFileNotFoundException;
import com.enatbanksc.ATMReconciliation.storage.StorageService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.RequiredArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import org.apache.poi.ss.usermodel.Workbook;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class FileUploadController {

    private final StorageService storageService;
    private final ETSTransactionService mService;


    @GetMapping("/")
    public String listUploadedFiles(Model model) {

        model.addAttribute("files", storageService.loadAll().map(
                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                        "serveFile", path.getFileName().toString()).build().toString())
                .collect(Collectors.toList()));

        return "uploadForm";
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {

        storageService.store(file);
        /**
         * *
         *
         */

        Resource fileResource = storageService.loadAsResource(file.getOriginalFilename());
        /**
         * passing uploaded xlx or xlxs file will return List<ETSTransaction>
         */
        getTransactions(fileResource).forEach(mService::store);

        //FileInputStream fis = (FileInFputStream) fileResource.getInputStream();
        /**
         *
         */
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

    private List<ETSTransaction> getTransactions(Resource fileResource) throws IOException {
        Workbook workbook = new HSSFWorkbook(fileResource.getInputStream());

        Sheet sheet = workbook.getSheetAt(0);
        Map<Integer, List<String>> data = new HashMap<>();
        ArrayList<ETSTransaction> transactions = new ArrayList<>();
        int i = 0;

        int[] cellsToSkip = {0, 2, 6, 10, 21};

        for (Row row : sheet) {
            ETSTransaction t = new ETSTransaction();
            if (row.getRowNum() < 5) {
                continue;
            }

            data.put(i, new ArrayList<>());
            for (Cell cell : row) {
                if (IntStream.of(cellsToSkip).anyMatch(x -> x == cell.getColumnIndex())) {
                    continue;
                }

//                switch (cell.getCellType()) {
//                    case STRING:
//                        data.get(i).add(cell.getRichStringCellValue().getString());
//
//                        break;
//                    case NUMERIC:
//                        if (DateUtil.isCellDateFormatted(cell)) {
//                            data.get(i).add(cell.getDateCellValue() + "");
//                        } else {
//                            data.get(i).add(cell.getNumericCellValue() + "");
//                        }
//                        break;
//                    case BOOLEAN:
//                        data.get(i).add(cell.getBooleanCellValue() + "");
//
//                        break;
//                    
//                    default:
//                        data.get(i).add("0");
//                }
                System.out.println(cell.getCellType() + "\t" + cell.getColumnIndex());
                switch (cell.getColumnIndex()) {
                    case 1:
                        t.setIssuer(cell.getRichStringCellValue().getString());
                        break;
                    case 3:
                        t.setAcquirer(cell.getRichStringCellValue().getString());
                        break;
                    case 4:
                        if (cell.getCellType() == CellType.NUMERIC) {
                            t.setMTI((int) cell.getNumericCellValue());
                        } else {
                            t.setMTI(Integer.parseInt(cell.getRichStringCellValue().getString()));
                        }

                        break;
                    case 5:
                        t.setCardNumber(cell.getRichStringCellValue().getString());
                        break;
                    case 7:
                        t.setAmount(Float.parseFloat(cell.getRichStringCellValue().getString()));

                        break;
                    case 8:
                        t.setCurrency(cell.getRichStringCellValue().getString());
                        break;
                    case 9:
                        var format = DateTimeFormatter.ofPattern("dd.MM.yyyy hh:mm:ss");
                        t.setTransactionDate(LocalDateTime.parse(cell.getRichStringCellValue().getString(), format));
                        break;

                    case 11:
                        t.setTransactionDesc(cell.getRichStringCellValue().getString());
                        break;
                    case 12:
                        t.setTerminalId(cell.getRichStringCellValue().getString());
                        break;
                    case 13:
                        t.setTransactionPlace(cell.getRichStringCellValue().getString());
                        break;
                    case 14:
                        if (cell.getCellType() == CellType.NUMERIC) {
                            t.setStan((int) cell.getNumericCellValue());
                        } else {
                            t.setStan(Integer.parseInt(cell.getRichStringCellValue().getString()));
                        }

                        break;
                    case 15:
                        t.setRefnumF37(cell.getRichStringCellValue().getString());
                        break;
                    case 16:
                        t.setAuthIdRespF38(cell.getRichStringCellValue().getString());
                        break;
                    case 17:
                        if (cell.getCellType() == CellType.NUMERIC) {
                            t.setFeUtrnno(String.valueOf(cell.getNumericCellValue()));
                        } else {
                            t.setFeUtrnno(cell.getRichStringCellValue().getString());
                        }
                        break;
                    case 18:
                        t.setBoUtrnno(cell.getRichStringCellValue().getString());
                        break;
                    case 19:
                        if (cell.getCellType() == CellType.NUMERIC) {
                            t.setFeeAmountOne((float) cell.getNumericCellValue());
                        } else {
                            t.setFeeAmountOne(Float.parseFloat(cell.getRichStringCellValue().getString()));
                        }

                        break;
                    case 20:
                        if (cell.getCellType() == CellType.NUMERIC) {
                            t.setFeeAmountTwo((float) cell.getNumericCellValue());
                        } else {
                            t.setFeeAmountTwo(Float.parseFloat(cell.getRichStringCellValue().getString()));
                        }
                        break;
                }
                transactions.add(t);
            }
            i++;
        }
        return transactions;

    }
}
