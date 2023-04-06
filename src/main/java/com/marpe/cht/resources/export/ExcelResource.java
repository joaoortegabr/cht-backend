package com.marpe.cht.resources.export;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.marpe.cht.services.export.ExcelService;

@CrossOrigin
@Controller
@RequestMapping(value = "/api/excel")
public class ExcelResource {

	  DateTimeFormatter dtfmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	  
	  @Autowired
	  ExcelService excelFileService;
	
	  @GetMapping(value="/coordenadores")
	  public ResponseEntity<Resource> exportCoordenadores() {
	    String filename = "coordenadores.xlsx";
	    InputStreamResource file = new InputStreamResource(excelFileService.listCoordenadores());
	
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	  
	  @GetMapping(value="/colaboradores")
	  public ResponseEntity<Resource> exportColaboradores() {
	    String filename = "colaboradores.xlsx";
	    InputStreamResource file = new InputStreamResource(excelFileService.listColaboradores());
	
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
	        .body(file);
	  }
	  
//	  @GetMapping(value="/colaboradoresPorCidade")
//	  public ResponseEntity<Resource> exportColaboradoresPorCidade(@RequestParam String cidade) {
//	    String filename = "colaboradoresPorCidade.xlsx";
//	    InputStreamResource file = new InputStreamResource(excelFileService.listColaboradoresPorCidade(cidade));
//	
//	    return ResponseEntity.ok()
//	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
//	        .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//	        .body(file);
//	  }
	  
	  @GetMapping(value="/os")
	  public ResponseEntity<Resource> exportOS() {
	    String filename = "os.xlsx";
	    InputStreamResource file = new InputStreamResource(excelFileService.listOS());
	
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	  
	  @GetMapping(value="/oscolab")
	  public ResponseEntity<Resource> exportOSColab() {
	    String filename = "oscolab.xlsx";
	    InputStreamResource file = new InputStreamResource(excelFileService.listOSColab());
	
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
	  
	  @GetMapping(value="/oscolabPorPeriodo")
	  public ResponseEntity<Resource> exportOSColabPorPeriodo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Boolean todosPagos) {
	    
		String filename = "oscolabPorPeriodo.xlsx";
	    InputStreamResource file = new InputStreamResource(excelFileService.OSColabPorPeriodo(startDate, endDate, todosPagos));
	
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }

	  @GetMapping(value="/oscolabSomadoPorPeriodo")
	  public ResponseEntity<Resource> exportOSColabSomadoPorPeriodo(@RequestParam String startDate, @RequestParam String endDate, @RequestParam Boolean todosPagos) {
	    
		String filename = "oscolabSomadoPorPeriodo.xlsx";
	    InputStreamResource file = new InputStreamResource(excelFileService.OSColabSomadoPorPeriodo(startDate, endDate, todosPagos));
	
	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
	        .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
	        .body(file);
	  }
  

}
