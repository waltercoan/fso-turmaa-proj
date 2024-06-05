package br.univille.projfso2024a.service;

import org.springframework.web.multipart.MultipartFile;

public interface SalvarArquivosService {
    String save(MultipartFile file);
}
