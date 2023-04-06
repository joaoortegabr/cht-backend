package com.marpe.cht.export.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.marpe.cht.entities.Colaborador;
import com.marpe.cht.entities.Coordenador;
import com.marpe.cht.entities.OS;
import com.marpe.cht.entities.OSColab;
import com.marpe.cht.repositories.ColaboradorRepository;

public class ExcelHelper {
	
	  @Autowired
	  static ColaboradorRepository colaboradorRepository;
	
	  public static NumberFormat nf = new DecimalFormat("#0.00");
	  
	  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	  public static ByteArrayInputStream coordenadoresToExcel(List<Coordenador> coordenadores) {
	
		String[] HEADERs = { "Id", "Nome", "CPF", "RG", "Email", "Telefone", "Regional", "Perfil", "Ativo" };
		String SHEET = "Coordenadores";
		  
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	
	      // Header
	      Row headerRow = sheet.createRow(0);
	
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	
	      int rowIdx = 1;
	      for (Coordenador coordenador : coordenadores) {
	        Row row = sheet.createRow(rowIdx++);
	
	        row.createCell(0).setCellValue(coordenador.getId());
	        row.createCell(1).setCellValue(coordenador.getUser().getNome());
	        row.createCell(2).setCellValue(coordenador.getUser().getCpf());
	        row.createCell(3).setCellValue(coordenador.getUser().getRg());
	        row.createCell(4).setCellValue(coordenador.getUser().getEmail());
	        row.createCell(5).setCellValue(coordenador.getUser().getTelefone());
	        row.createCell(6).setCellValue(coordenador.getRegional().getNome());
	        row.createCell(7).setCellValue(coordenador.getUser().getPerfil().toString());
	        row.createCell(8).setCellValue(coordenador.getUser().getAtivo());
	      }
	
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
	    }
	  }
	  
	  
	  public static ByteArrayInputStream colaboradoresToExcel(List<Colaborador> colaboradores) {
			
		String[] HEADERs = { "Id", "Nome", "CPF", "RG", "Email", "Telefone", "Cidade", "Banco", "Agencia", "Conta", "Operacao", "Detalhe", "Perfil", "Ativo" };
		String SHEET = "Colaboradores";
		  
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	
	      // Header
	      Row headerRow = sheet.createRow(0);
	
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	
	      int rowIdx = 1;
	      for (Colaborador colaborador : colaboradores) {
	        Row row = sheet.createRow(rowIdx++);
	
	        row.createCell(0).setCellValue(colaborador.getId());
	        row.createCell(1).setCellValue(colaborador.getUser().getNome());
	        row.createCell(2).setCellValue(colaborador.getUser().getCpf());
	        row.createCell(3).setCellValue(colaborador.getUser().getRg());
	        row.createCell(4).setCellValue(colaborador.getUser().getEmail());
	        row.createCell(5).setCellValue(colaborador.getUser().getTelefone());
	        row.createCell(6).setCellValue(colaborador.getCidade().getNome());
	        row.createCell(7).setCellValue(colaborador.getBanco());
	        row.createCell(8).setCellValue(colaborador.getAgencia());
	        row.createCell(9).setCellValue(colaborador.getConta());
	        row.createCell(10).setCellValue(colaborador.getOperacao());
	        row.createCell(11).setCellValue(colaborador.getTitular());
	        row.createCell(12).setCellValue(colaborador.getUser().getPerfil().toString());
	        row.createCell(13).setCellValue(colaborador.getUser().getAtivo());
	      }
	
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
	    }
	  }
	  
	  
	  public static ByteArrayInputStream colaboradoresPorCidadeToExcel(List<Colaborador> colaboradores, String cidade) {
			
		String[] HEADERs = { "Nome", "CPF", "RG", "Telefone", "Cidade", "Ativo" };
		String SHEET = "Colaboradores Por Cidade";
		  
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	
	      // Header
	      Row headerRow = sheet.createRow(0);
	
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	
	      int rowIdx = 1;
	      for (Colaborador colaborador : colaboradores) {
	    	  if(colaborador.getCidade().getNome().equals(cidade)) {
	    	  
	    		  Row row = sheet.createRow(rowIdx++);
	        
			      row.createCell(0).setCellValue(colaborador.getUser().getNome());
			      row.createCell(1).setCellValue(colaborador.getUser().getCpf());
			      row.createCell(2).setCellValue(colaborador.getUser().getRg());
			      row.createCell(3).setCellValue(colaborador.getUser().getTelefone());
			      row.createCell(4).setCellValue(colaborador.getCidade().getNome());
			      row.createCell(5).setCellValue(colaborador.getUser().getAtivo());
		      }
	      }
	
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
	    }
	  }

	  

	  
	  public static ByteArrayInputStream osToExcel(List<OS> oss) {
			
		String[] HEADERs = { "Id", "Cliente", "Regional", "Coordenador", "DataInício", "HoraInício", "Obs", "TodosPagos", "Concluída", "Status" };
		String SHEET = "OS";
		  
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	
	      // Header
	      Row headerRow = sheet.createRow(0);
	
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	
	      int rowIdx = 1;
	      for (OS os : oss) {
	        Row row = sheet.createRow(rowIdx++);
	
	        row.createCell(0).setCellValue(os.getId());
	        row.createCell(1).setCellValue(os.getCliente().getNome());
	        row.createCell(2).setCellValue(os.getRegional().getNome());
	        row.createCell(3).setCellValue(os.getCoordenador().getUser().getNome());
	        row.createCell(4).setCellValue(os.getDataInicio().toString());
	        row.createCell(5).setCellValue(os.getHoraInicio().toString());
	        row.createCell(6).setCellValue(os.getObservacao());
	        row.createCell(7).setCellValue(os.getTodosPagos());
	        row.createCell(8).setCellValue(os.getConcluida());
	        row.createCell(9).setCellValue(os.getState().toString());
	      }
	
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
	    }
	  }
	  
	  
	  public static ByteArrayInputStream oscolabToExcel(List<OSColab> oscolabs) {
			
		String[] HEADERs = { "Id", "OS", "Cliente", "Coordenador", "DataInício", "Hora Inicial", "Hora Final", "Total Horas Diurnas", 
				"Total Horas Noturnas", "Fez intervalo", "Transportes usados", "Colaborador", "Total a receber", "Foi pago", 
				"Banco", "Agencia", "Conta", "Operacao", "Detalhe" };
		String SHEET = "Colaboradores na OS";
		  
		
		
	    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
	      Sheet sheet = workbook.createSheet(SHEET);
	
	      // Header
	      Row headerRow = sheet.createRow(0);
	
	      for (int col = 0; col < HEADERs.length; col++) {
	        Cell cell = headerRow.createCell(col);
	        cell.setCellValue(HEADERs[col]);
	      }
	
	      int rowIdx = 1;
	      for (OSColab oscolab : oscolabs) {
	        Row row = sheet.createRow(rowIdx++);
	
	        row.createCell(0).setCellValue(oscolab.getId());
	        row.createCell(1).setCellValue(oscolab.getOs().getId());
	        row.createCell(2).setCellValue(oscolab.getOs().getCliente().getNome());
	        row.createCell(3).setCellValue(oscolab.getOs().getCoordenador().getUser().getNome());
	        row.createCell(4).setCellValue(oscolab.getOs().getDataInicio().toString());
	        if(oscolab.getHoraInicial() == null) {
	        	row.createCell(5).setCellValue("n/a");
	        } else {
		        row.createCell(5).setCellValue(oscolab.getHoraInicial().toString());
	        }
	        if(oscolab.getHoraFinal() == null) {
	        	row.createCell(6).setCellValue("n/a");
	        } else {
	        	row.createCell(6).setCellValue(oscolab.getHoraFinal().toString());
	        }
	        if(oscolab.getTotalHorasDiurnas() == null) {
	        	row.createCell(7).setCellValue("n/a");
	        } else {
		        row.createCell(7).setCellValue(oscolab.getTotalHorasDiurnas());
	        }
	        if(oscolab.getTotalHorasNoturnas() == null) {
	        	row.createCell(8).setCellValue("n/a");
	        } else {
		        row.createCell(8).setCellValue(oscolab.getTotalHorasNoturnas());
	        }
	        row.createCell(9).setCellValue(oscolab.getIntervalo());
	        row.createCell(10).setCellValue(oscolab.getTransportes());
	        row.createCell(11).setCellValue(oscolab.getColaborador().getUser().getNome());
	        if(oscolab.getTotalAReceber() == null) {
	        	row.createCell(12).setCellValue("n/a");
	        } else {
		        row.createCell(12).setCellValue(nf.format(oscolab.getTotalAReceber()));
	        }
	        row.createCell(13).setCellValue(oscolab.getPago());
	        row.createCell(14).setCellValue(oscolab.getColaborador().getBanco());
	        row.createCell(15).setCellValue(oscolab.getColaborador().getAgencia());
	        row.createCell(16).setCellValue(oscolab.getColaborador().getConta());
	        row.createCell(17).setCellValue(oscolab.getColaborador().getOperacao());
	        row.createCell(18).setCellValue(oscolab.getColaborador().getTitular());

	      }
	
	      workbook.write(out);
	      return new ByteArrayInputStream(out.toByteArray());
	    } catch (IOException e) {
	      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
	    }
	  }
	  
	  public static ByteArrayInputStream oscolabPorPeriodo(List<OSColab> oscolabs) {
			
			String[] HEADERs = { "Id", "OS", "Colaborador", "Total a receber", "Foi pago", "Cliente", "Coordenador", 
					"DataInício", "Hora Inicial", "Hora Final", "Total Horas Diurnas", "Total Horas Noturnas", "Fez intervalo", "Transportes usados" };
			String SHEET = "Atividades por Periodo";
			  
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
		      Sheet sheet = workbook.createSheet(SHEET);
		
		      // Header
		      Row headerRow = sheet.createRow(0);
		
		      for (int col = 0; col < HEADERs.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(HEADERs[col]);
		      }
		
		      int rowIdx = 1;
		      for (OSColab oscolab : oscolabs) {
		        Row row = sheet.createRow(rowIdx++);
		
		        row.createCell(0).setCellValue(oscolab.getId());
		        row.createCell(1).setCellValue(oscolab.getOs().getId());
		        row.createCell(2).setCellValue(oscolab.getColaborador().getUser().getNome());
		        row.createCell(3).setCellValue(nf.format(oscolab.getTotalAReceber()));
		        row.createCell(4).setCellValue(oscolab.getPago());
		        row.createCell(5).setCellValue(oscolab.getOs().getCliente().getNome());
		        row.createCell(6).setCellValue(oscolab.getOs().getCoordenador().getUser().getNome());
		        row.createCell(7).setCellValue(oscolab.getOs().getDataInicio().toString());
		        row.createCell(8).setCellValue(oscolab.getHoraInicial().toString());
		        row.createCell(9).setCellValue(oscolab.getHoraFinal().toString());
		        row.createCell(10).setCellValue(oscolab.getTotalHorasDiurnas());
		        row.createCell(11).setCellValue(oscolab.getTotalHorasNoturnas());
		        row.createCell(12).setCellValue(oscolab.getIntervalo());
		        row.createCell(13).setCellValue(oscolab.getTransportes());

		      }
		
		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
		    }
		  }
	  
	  
	  
//	  public static ByteArrayInputStream oscolabPorPeriodoEStatus(List<OSColab> oscolabs, Boolean status) {
//			
//			String[] HEADERs = { "Id", "OS", "Colaborador", "Total a receber", "Foi pago", "Cliente", "Coordenador", 
//					"DataInício", "Hora Inicial", "Hora Final", "Total Horas Diurnas", "Total Horas Noturnas", "Fez intervalo", "Transportes usados" };
//			String SHEET = "Colaboradores por Periodo";
//			  
//		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
//		      Sheet sheet = workbook.createSheet(SHEET);
//		
//		      // Header
//		      Row headerRow = sheet.createRow(0);
//		
//		      for (int col = 0; col < HEADERs.length; col++) {
//		        Cell cell = headerRow.createCell(col);
//		        cell.setCellValue(HEADERs[col]);
//		      }
//		
//		      int rowIdx = 1;
//		      for (OSColab oscolab : oscolabs) {
//		    	  
//		          Row row = sheet.createRow(rowIdx++);
//						
//			        row.createCell(0).setCellValue(oscolab.getId());
//			        row.createCell(1).setCellValue(oscolab.getOs().getId());
//			        row.createCell(2).setCellValue(oscolab.getColaborador().getUser().getNome());
//			        row.createCell(3).setCellValue(oscolab.getTotalAReceber());
//			        row.createCell(4).setCellValue(oscolab.getPago());
//			        row.createCell(5).setCellValue(oscolab.getOs().getCliente().getNome());
//			        row.createCell(6).setCellValue(oscolab.getOs().getCoordenador().getUser().getNome());
//			        row.createCell(7).setCellValue(oscolab.getOs().getDataInicio().toString());
//			        row.createCell(8).setCellValue(oscolab.getHoraInicial().toString());
//			        row.createCell(9).setCellValue(oscolab.getHoraFinal().toString());
//			        row.createCell(10).setCellValue(oscolab.getTotalHorasDiurnas());
//			        row.createCell(11).setCellValue(oscolab.getTotalHorasNoturnas());
//			        row.createCell(12).setCellValue(oscolab.getIntervalo());
//			        row.createCell(13).setCellValue(oscolab.getTransportes());
//
//		      }
//		
//		      workbook.write(out);
//		      return new ByteArrayInputStream(out.toByteArray());
//		    } catch (IOException e) {
//		      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
//		    }
//		  }
//	  
	  
//	  public static ByteArrayInputStream oscolabPorPeriodoEPago(List<Colaborador> colaboradores, List<OSColab> oscolabs) {
//			
//			String[] HEADERs = { "Id", "OS", "Colaborador", "Total a receber", "Foi pago", "Cliente", "Coordenador", 
//					"DataInício", "Hora Inicial", "Hora Final", "Total Horas Diurnas", "Total Horas Noturnas", "Fez intervalo", "Transportes usados" };
//			String SHEET = "Colaboradores por Periodo";
//			  
//		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
//		      Sheet sheet = workbook.createSheet(SHEET);
//		
//		      // Header
//		      Row headerRow = sheet.createRow(0);
//		
//		      for (int col = 0; col < HEADERs.length; col++) {
//		        Cell cell = headerRow.createCell(col);
//		        cell.setCellValue(HEADERs[col]);
//		      }
//		
//		      int rowIdx = 1;
//		      for (OSColab oscolab : oscolabs) {
//		    	  
//		          Row row = sheet.createRow(rowIdx++);
//						
//			        row.createCell(0).setCellValue(oscolab.getId());
//			        row.createCell(1).setCellValue(oscolab.getOs().getId());
//			        row.createCell(2).setCellValue(oscolab.getColaborador().getUser().getNome());
//			        row.createCell(3).setCellValue(oscolab.getTotalAReceber());
//			        row.createCell(4).setCellValue(oscolab.getPago());
//			        row.createCell(5).setCellValue(oscolab.getOs().getCliente().getNome());
//			        row.createCell(6).setCellValue(oscolab.getOs().getCoordenador().getUser().getNome());
//			        row.createCell(7).setCellValue(oscolab.getOs().getDataInicio().toString());
//			        row.createCell(8).setCellValue(oscolab.getHoraInicial().toString());
//			        row.createCell(9).setCellValue(oscolab.getHoraFinal().toString());
//			        row.createCell(10).setCellValue(oscolab.getTotalHorasDiurnas());
//			        row.createCell(11).setCellValue(oscolab.getTotalHorasNoturnas());
//			        row.createCell(12).setCellValue(oscolab.getIntervalo());
//			        row.createCell(13).setCellValue(oscolab.getTransportes());
//
//		      }
//		
//		      workbook.write(out);
//		      return new ByteArrayInputStream(out.toByteArray());
//		    } catch (IOException e) {
//		      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
//		    }
//		  }
//	  
	  
	  public static ByteArrayInputStream oscolabSomadoPorPeriodo(List<Colaborador> colaboradores, List<Object[]> result) {
			
			String[] HEADERs = { "Total a receber", "Colaborador", "Banco", "Agencia", "Conta", "Operacao", "Detalhe" };
			String SHEET = "Atividades Somado por Periodo";
			  
		    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
		      Sheet sheet = workbook.createSheet(SHEET);
		
		      // Header
		      Row headerRow = sheet.createRow(0);
		
		      for (int col = 0; col < HEADERs.length; col++) {
		        Cell cell = headerRow.createCell(col);
		        cell.setCellValue(HEADERs[col]);
		      }

		      int rowIdx = 1;
		      for (Object[] x : result) {
		    	  
		          Row row = sheet.createRow(rowIdx++);
				
			      row.createCell(0).setCellValue(nf.format(Double.parseDouble(x[1].toString())));
		          for(Colaborador c : colaboradores) {
		        	  if(x[0].toString().equals(c.getId().toString())) {
		        		  row.createCell(1).setCellValue(c.getUser().getNome());
		        		  row.createCell(2).setCellValue(c.getBanco());
		        		  row.createCell(3).setCellValue(c.getAgencia());
		        		  row.createCell(4).setCellValue(c.getConta());
		        		  row.createCell(5).setCellValue(c.getOperacao());
		        		  row.createCell(6).setCellValue(c.getTitular());
		        	  }
		          }


		      }
		
		      workbook.write(out);
		      return new ByteArrayInputStream(out.toByteArray());
		    } catch (IOException e) {
		      throw new RuntimeException("Failed to import data to Excel file: " + e.getMessage());
		    }
		  }
	  

}