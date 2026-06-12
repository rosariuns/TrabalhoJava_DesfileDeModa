package com.unipar.desfile.services;

import com.unipar.desfile.models.Contrato;
import com.unipar.desfile.models.Desfile;
import com.unipar.desfile.models.Modelo;
import com.unipar.desfile.repositories.ContratoRepository;
import com.unipar.desfile.repositories.DesfileRepository;
import com.unipar.desfile.repositories.ModeloRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContratoService {

    private final DesfileRepository desfileRepository;
    private final ModeloRepository modeloRepository;
    private final ContratoRepository contratoRepository;

    public ContratoService(DesfileRepository desfileRepo, ModeloRepository modeloRepo, ContratoRepository contratoRepo) {
        this.desfileRepository = desfileRepo;
        this.modeloRepository = modeloRepo;
        this.contratoRepository = contratoRepo;
    }

    // AQUI ESTÁ O REQUISITO TRANSACIONAL!
    @Transactional
    public Contrato fecharContrato(Long desfileId, Long modeloId, String look) {
        
        Desfile desfile = desfileRepository.findById(desfileId)
                .orElseThrow(() -> new RuntimeException("Desfile não encontrado!"));
        
        Modelo modelo = modeloRepository.findById(modeloId)
                .orElseThrow(() -> new RuntimeException("Modelo não encontrada!"));

        // REGRA DE NEGÓCIO 1: Validar Orçamento
        if (desfile.getOrcamentoTotal() < modelo.getCache()) {
            throw new RuntimeException("Orçamento insuficiente para contratar a modelo " + modelo.getNome());
        }

        // Abate o valor do cachê do orçamento do desfile
        desfile.setOrcamentoTotal(desfile.getOrcamentoTotal() - modelo.getCache());
        desfileRepository.save(desfile); // Atualiza o desfile no banco

        // REGRA DE NEGÓCIO 2 (Caso de uso 3): Atribuir Look e Criar Contrato
        Contrato contrato = new Contrato();
        contrato.setDesfile(desfile);
        contrato.setModelo(modelo);
        contrato.setLookAtribuido(look);

        // Salva o contrato
        return contratoRepository.save(contrato);
    }
}