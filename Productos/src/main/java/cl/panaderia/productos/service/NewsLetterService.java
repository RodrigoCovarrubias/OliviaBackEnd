package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.NewsLetterRequest;
import cl.panaderia.productos.dto.ServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsLetterService {

    @Autowired
    private ServiceDto newsLetterDto;

    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String REGEX_NAME = "[a-zA-Z]";

    public boolean suscribe(NewsLetterRequest newsLetter) {
        if (newsLetter.getNombre() == null || newsLetter.getCorreo() == null
                || !newsLetter.getCorreo().matches(REGEX_EMAIL)
                || newsLetter.getNombre().matches(REGEX_NAME)) {
            return false;
        }
        return newsLetterDto.suscribe(newsLetter);
    }

    public List<NewsLetterRequest> getAllSuscribers() {
        return newsLetterDto.getAllSuscribers();
    }


}
