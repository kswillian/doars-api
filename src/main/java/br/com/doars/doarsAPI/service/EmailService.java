package br.com.doars.doarsAPI.service;

import br.com.doars.doarsAPI.domain.Doador;
import br.com.doars.doarsAPI.domain.Entidade;
import br.com.doars.doarsAPI.domain.TipoSanguineo;
import br.com.doars.doarsAPI.domain.Usuario;
import br.com.doars.doarsAPI.util.Utilidades;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
@AllArgsConstructor
public class EmailService {

    private JavaMailSender mailSender;
    private SpringTemplateEngine templateEngine;
    private Utilidades utilidades;

    @Async
    public void sendSimpleEmail(String to, String subject, String text){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        try {

            mailSender.send(simpleMailMessage);
            Thread.sleep(5000);

        }catch (InterruptedException e ){
            e.printStackTrace();
        }

        System.out.println("Email enviado...");

    }

    @Async
    public void sendEmailSolicitacao(List<String> emails, Entidade entidade, TipoSanguineo tipoSanguineo){

        if(emails.size() > 0){

            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(utilidades.convertListToString(emails));
            simpleMailMessage.setSubject("Necessidade de sangue " + tipoSanguineo.getDescricao());
            simpleMailMessage.setText("Olá doador, a " + entidade.getNome() + "necessita de doação do sangue " + tipoSanguineo.getDescricao());

            try{
                mailSender.send(simpleMailMessage);
                Thread.sleep(5000);

            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("Email enviado...");

        }

    }

    @Async
    public void sendEmailSolicitacao(List<String> emails, Entidade entidade, List<TipoSanguineo> tipoSanguineos){

        if(emails.size() > 0){

            String tiposSanguineos = utilidades.convertListTiposSanguineosToString(tipoSanguineos);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(utilidades.convertListToString(emails));
            simpleMailMessage.setSubject("Necessidade de sangue " + tiposSanguineos);
            simpleMailMessage.setText("Olá doador, a " + entidade.getNome() + " necessita de doação do sangue do(s) tipo(s) " + tiposSanguineos);

            try{
                mailSender.send(simpleMailMessage);
                Thread.sleep(5000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("Email enviado ... ");

        }

    }

    @Async
    public void sendEmailRegisterDoador(Doador doador){

        Context context = new Context();
        context.setVariable("nome", doador.getNome());

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message;

        try {

            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("CONFIRMAÇÃO DE CADASTRO");
            message.setTo(doador.getContato().getEmail());
            String htmlContent = templateEngine.process("cadastro-doador.html", context);
            message.setText(htmlContent, true);
            System.out.println("Email enviado para: " + doador.getContato().getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);

    }

    @Async
    public void sendEmailRegisterEntidade(Entidade entidade, Usuario usuario){

        Context context = new Context();
        context.setVariable("codigo", usuario.getCodigoValidacao());
        context.setVariable("nome", entidade.getNome());

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message;

        try {

            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("CONFIRMAÇÃO DE CADASTRO");
            message.setTo(entidade.getContato().getEmail());
            String htmlContent = templateEngine.process("cadastro-entidade.html", context);
            message.setText(htmlContent, true);
            System.out.println("Email enviado para: " + entidade.getContato().getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);

    }

    @Async
    public void sendEmailUpdateEntidade(Entidade entidade, Usuario usuario){

        Context context = new Context();
        context.setVariable("codigo", usuario.getCodigoValidacao());
        context.setVariable("nome", entidade.getNome());

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper message;

        try {

            message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setSubject("ALTERAÇÃO DE CADASTRO");
            message.setTo(entidade.getContato().getEmail());
            String htmlContent = templateEngine.process("cadastro-entidade.html", context);
            message.setText(htmlContent, true);
            System.out.println("Email enviado para: " + entidade.getContato().getEmail());

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        mailSender.send(mimeMessage);

    }

}
