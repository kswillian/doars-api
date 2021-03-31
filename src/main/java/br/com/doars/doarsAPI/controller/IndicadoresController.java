package br.com.doars.doarsAPI.controller;

import br.com.doars.doarsAPI.controller.dto.*;
import br.com.doars.doarsAPI.service.DoadorService;
import br.com.doars.doarsAPI.service.EntidadeService;
import br.com.doars.doarsAPI.service.SolicitacaoDoacaoService;
import br.com.doars.doarsAPI.service.TipoSanguineoService;
import br.com.doars.doarsAPI.util.Utilidades;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/indicadores")
@Api(tags = "Dashboard (Dashboard)")
public class IndicadoresController {

    private EntidadeService entidadeService;
    private DoadorService doadorService;
    private TipoSanguineoService tipoSanguineoService;
    private SolicitacaoDoacaoService solicitacaoDoacaoService;

    @GetMapping("/gerais")
    public ResponseEntity<DashboardGeralDTO> listAllGerais(){

        Long endidades = entidadeService.countEntidadesAtivas();
        Long doadores = doadorService.countDoadoresAtivos();
        Long solicitacoes = solicitacaoDoacaoService.countSolicitacoesDoacaoAtivas();

        return ResponseEntity.status(HttpStatus.OK).body(
                new DashboardGeralDTO(endidades, doadores, solicitacoes, new Utilidades().formatDate(LocalDateTime.now()))
        );

    }

    @GetMapping("/doadores-tipos-sanguineos")
    public ResponseEntity<DashboardDoadoresTipoSanguineoDTO> listIndicadorDoadoresByTipoSanguineo(){

        List<TipoSanguineoDTO> tipoSanguineos = tipoSanguineoService.listAll();
        List<DoadoresTipoSanguineoDTO> doadoresTipoSanguineo = new ArrayList<>();

        for (TipoSanguineoDTO tipoSanguineo: tipoSanguineos){
            doadoresTipoSanguineo.add(new DoadoresTipoSanguineoDTO(
                    tipoSanguineo,
                    (long) doadorService.listByTipoSanguineo(tipoSanguineo.getId()).size()
            ));
        }

        DashboardDoadoresTipoSanguineoDTO sanguineoDTO = new DashboardDoadoresTipoSanguineoDTO(
                doadoresTipoSanguineo, new Utilidades().formatDate(LocalDateTime.now()));

        return ResponseEntity.status(HttpStatus.OK).body(sanguineoDTO);

    }

    @GetMapping("/{id}/solicitacoes-tipos-sanguineos")
    public ResponseEntity<DashboardSolicitacaoDoacaoTipoSanguineoDTO> listIndicadorSolicitacaoDoacaoByTipoSanguineo(@PathVariable Long id){

        entidadeService.validationEntidadeParam(id);
        List<TipoSanguineoDTO> tipoSanguineos = tipoSanguineoService.listAll();
        List<SolicitacaoDoacaoTipoSanguineoDTO> solicitacaoDoacaoTipoSanguineo = new ArrayList<>();

        for(TipoSanguineoDTO tipoSanguineo: tipoSanguineos){
            solicitacaoDoacaoTipoSanguineo.add(new SolicitacaoDoacaoTipoSanguineoDTO(
                    tipoSanguineo,
                    solicitacaoDoacaoService.countSolicitacoesDoacaoAtivasByEntidadeAndTipoSanguineo(id, tipoSanguineo.getId())
            ));
        }

        DashboardSolicitacaoDoacaoTipoSanguineoDTO dashboardSolicitacaoDoacaoTipoSanguineoDTO = new DashboardSolicitacaoDoacaoTipoSanguineoDTO(
                solicitacaoDoacaoTipoSanguineo, new Utilidades().formatDate(LocalDateTime.now()));

        return ResponseEntity.status(HttpStatus.OK).body(dashboardSolicitacaoDoacaoTipoSanguineoDTO);
    }

}
