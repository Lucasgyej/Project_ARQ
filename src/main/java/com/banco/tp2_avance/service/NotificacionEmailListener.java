package com.banco.tp2_avance.service;

import com.banco.tp2_avance.event.ClienteRegistradoEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacionEmailListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionEmailListener.class);

    @Async
    @EventListener
    public void manejarClienteRegistrado(ClienteRegistradoEvent evento) {
        String urlActivacion = "http://localhost:8080/api/v1/clientes/activar?token=" + evento.getToken();

        String cuerpoHtml = """
                <html>
                    <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                        <div style="max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 8px;">
                            <h2 style="color: #2c3e50;">¡Bienvenido/a al Banco, %s!</h2>
                            <p>Tu solicitud de registro ha sido procesada con éxito.</p>
                            <p>Para activar tu cuenta bancaria y comenzar a operar, por favor hacé clic en el siguiente botón:</p>
                            <p style="text-align: center;">
                                <a href="%s" style="background-color: #27ae60; color: white; padding: 12px 20px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">Activar mi Cuenta</a>
                            </p>
                            <p style="font-size: 12px; color: #7f8c8d;">Este enlace tiene una validez de 24 horas.</p>
                        </div>
                    </body>
                </html>
                """.formatted(evento.getNombreRazonSocial(), urlActivacion);

        log.info("================== INICIO ENVÍO DE EMAIL ASÍNCRONO ==================");
        log.info("Destinatario: {}", evento.getEmail());
        log.info("Asunto: Activación de tu cuenta bancaria");
        log.info("Cuerpo HTML generado:\n{}", cuerpoHtml);
        log.info("================== FIN ENVÍO DE EMAIL ASÍNCRONO ====================");
    }
}