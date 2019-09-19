/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enatbanksc.ATMReconciliation.reconciliation;

import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransaction;
import com.enatbanksc.ATMReconciliation.etswitch.transaction.ETSTransactionService;
import com.enatbanksc.ATMReconciliation.storage.StorageFileNotFoundException;
import com.enatbanksc.ATMReconciliation.storage.StorageService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class FileUploadController {

    private final StorageService storageService;
    @Autowired
    private ETSTransactionService mService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {

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
        getTransactions(fileResource).forEach(t -> mService.store(t));

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
                        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                         {
                            try {
                                t.setTransactionDate(format.parse(cell.getRichStringCellValue().getString()));
                            } catch (ParseException ex) {
                                Logger.getLogger(FileUploadController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
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
