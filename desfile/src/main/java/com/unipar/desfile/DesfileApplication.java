package com.unipar.desfile; // Mude para o seu pacote se for diferente

import com.unipar.desfile.models.Desfile;
import com.unipar.desfile.models.Modelo;
import com.unipar.desfile.repositories.DesfileRepository;
import com.unipar.desfile.repositories.ModeloRepository;
import com.unipar.desfile.services.ContratoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class DesfileApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesfileApplication.class, args);
    }

    @Bean
    public CommandLineRunner run(DesfileRepository desfileRepo, 
                                 ModeloRepository modeloRepo, 
                                 ContratoService contratoService) {
        return args -> {
            System.out.println("===========================================");
            System.out.println("INICIANDO O SISTEMA DO DESFILE DE MODA...");
            System.out.println("===========================================");

            // 1. CRUD BÁSICO: Criando um Desfile com orçaamento de R$ 10.000
            Desfile spfw = new Desfile();
            spfw.setNome("São Paulo Fashion Week");
            spfw.setData(LocalDate.of(2026, 8, 15));
            spfw.setOrcamentoTotal(10000.0);
            spfw = desfileRepo.save(spfw);
            System.out.println("-> Desfile Criado! Orçamento inicial: R$ " + spfw.getOrcamentoTotal());

            // 2. CRUD BÁSICO: Criando Modelos
            Modelo gisele = new Modelo();
            gisele.setNome("Gisele Bündchen");
            gisele.setCache(8000.0);
            gisele = modeloRepo.save(gisele);

            Modelo naomi = new Modelo();
            naomi.setNome("Naomi Campbell");
            naomi.setCache(5000.0);
            naomi = modeloRepo.save(naomi);
            System.out.println("-> Modelos cadastradas!");

            // 3. TRANSAÇÃO E REGRA DE NEGÓCIO: Contratando a Gisele
            System.out.println("\nTentando contratar: " + gisele.getNome() + " (Cachê: R$ 8000)");
            contratoService.fecharContrato(spfw.getId(), gisele.getId(), "Vestido de Gala Vermelho");
            
            // Buscando o desfile atualizado para ver o saldo
            Desfile desfileAtualizado = desfileRepo.findById(spfw.getId()).get();
            System.out.println("-> SUCESSO! Contrato fechado. Saldo restante: R$ " + desfileAtualizado.getOrcamentoTotal());

            // 4. TESTANDO O ERRO (ROLLBACK): Tentando contratar a Naomi (custa 5000, mas só sobrou 2000)
            System.out.println("\nTentando contratar: " + naomi.getNome() + " (Cachê: R$ 5000)");
            try {
                contratoService.fecharContrato(spfw.getId(), naomi.getId(), "Terno de Alfaiataria");
            } catch (Exception e) {
                System.out.println("-> ERRO ESPERADO PELA REGRA DE NEGÓCIO: " + e.getMessage());
                System.out.println("-> A transação falhou e o banco fez o Rollback (nada foi salvo).");
            }

            System.out.println("\n===========================================");
            System.out.println("EXECUÇÃO FINALIZADA COM SUCESSO!");
            System.out.println("===========================================");
        };
    }
}