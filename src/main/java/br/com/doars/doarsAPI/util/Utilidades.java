package br.com.doars.doarsAPI.util;

import br.com.doars.doarsAPI.domain.TipoSanguineo;
import com.google.common.collect.Lists;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import br.com.doars.doarsAPI.controller.dto.DoadorDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class Utilidades {

    public List<String> findAllEmailFromDoador(List<DoadorDTO> doadores){

        List<String> emails = new ArrayList<>();

        for(DoadorDTO doador: doadores){
            emails.add(doador.getContato().getEmail());
        }

        return emails;

    }

    public List<TipoSanguineo> convertSetToList(Set<TipoSanguineo> set){
        return Lists.newArrayList(set);
    }

    public List<Long> convertStringToList(String numbers){
        return new ArrayList<>(Arrays.asList(numbers.split(",")).stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList()));
    }

    public String[] convertListToString(List<String> list){

        String[] string = new String[list.size()];

        System.out.println("Lista de Email" + list.toString());

        for(int i = 0; i < list.size(); i++){
            string[i] = list.get(i);
        }

        return string;

    }

    public String convertListTiposSanguineosToString(List<TipoSanguineo> list){

        String string = "";

        for(int i = 0; i < list.size(); i++){
            string += list.get(i).getDescricao() + " ";
        }

        return string;

    }

    public String generateBCrypt(String string){
        return new BCryptPasswordEncoder().encode(string);
    }

    public Integer generateValidationCode(){

        String code = "";
        Random random = new Random();

        for(int i = 0; i < 4; i++){
            code += random.nextInt(10);
        }

        return Integer.parseInt(code);

    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public String formatDate(LocalDateTime date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm");
        return date.format(formatter);
    }

}
