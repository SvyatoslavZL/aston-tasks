package org.aston.application.service;

import org.aston.application.dto.LanguageTo;
import org.aston.application.entity.Language;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.Repository;

import java.util.Collection;
import java.util.Optional;

public class LanguageService {

    private final Repository<Language> languageRepository;

    public LanguageService(Repository<Language> languageRepository) {
        this.languageRepository = languageRepository;
    }

    public void create(LanguageTo languageTo) {
        languageRepository.create(EntityMapper.from(languageTo));
    }

    public Collection<LanguageTo> getAll() {
        return languageRepository.getAll().stream().map(EntityMapper::from).toList();
    }

    public Optional<LanguageTo> get(long id) {
        Language languagePattern = new Language(id);
        return languageRepository.find(languagePattern).map(EntityMapper::from).findFirst();
    }

    public Optional<LanguageTo> find(String name) {
        Language languagePattern = new Language(name);
        return languageRepository.find(languagePattern).map(EntityMapper::from).findAny();
    }

    public void update(LanguageTo languageTo) {
        languageRepository.update(EntityMapper.from(languageTo));
    }

    public void delete(LanguageTo languageTo) {
        languageRepository.delete(EntityMapper.from(languageTo));
    }
}
