package org.aston.application.service;

import org.aston.application.dto.LanguageTo;
import org.aston.application.entity.Language;
import org.aston.application.mapping.EntityMapper;
import org.aston.application.repository.LanguageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

class LanguageServiceTest {

    private static final String TEST_LANGUAGE = "testLanguage";

    private Language language;
    private LanguageTo languageTo;
    private LanguageRepository languageRepositoryMock;
    private LanguageService languageService;

    @BeforeEach
    void setUp() {
        language = new Language(1L, TEST_LANGUAGE);
        languageTo = EntityMapper.from(language);
        languageRepositoryMock = mock(LanguageRepository.class);
        when(languageRepositoryMock.find(any(Language.class))).thenReturn(Stream.of(language));
        languageService = new LanguageService(languageRepositoryMock);
    }

    @Test
    void getAll_shouldReturnAllLanguages() {
        when(languageRepositoryMock.getAll()).thenReturn(List.of(language, language));

        Collection<LanguageTo> all = languageService.getAll();
        assertEquals(2, all.size());
        assertTrue(all.contains(languageTo));
        verify(languageRepositoryMock).getAll();
    }

    @Test
    void getById_shouldReturnCorrectLanguage() {
        Optional<LanguageTo> optional = languageService.get(1L);
        LanguageTo actualLanguageTo = optional.orElseThrow();
        assertEquals(languageTo, actualLanguageTo);
    }

    @Test
    void findByLoginAndPassword_shouldReturnCorrectLanguage() {
        Optional<LanguageTo> optional = languageService.find(TEST_LANGUAGE);

        assertEquals(languageTo, optional.orElseThrow());
        verify(languageRepositoryMock).find(any(Language.class));
    }

    @Test
    void create_shouldInvokeCreateMethod() {
        languageService.create(languageTo);
        verify(languageRepositoryMock).create(any(Language.class));
    }

    @Test
    void update_shouldInvokeUpdateMethod() {
        languageService.update(languageTo);
        verify(languageRepositoryMock).update(any(Language.class));
    }

    @Test
    void delete_shouldInvokeDeleteMethod() {
        languageService.delete(languageTo);
        verify(languageRepositoryMock).delete(any(Language.class));
    }
}