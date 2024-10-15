package org.aston.application.repository;

import org.aston.application.ConnectionPoolManager;
import org.aston.application.TestContainer;
import org.aston.application.entity.Language;
import org.aston.application.exception.DaoException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class LanguageRepositoryTest extends TestContainer implements ConnectionPoolManager {

    private static final String TEST_LANGUAGE = "testLanguage";
    private static final String ANOTHER_TEST_LANGUAGE = "anotherTestLanguage";
    private LanguageRepository languageRepository;
    private Language testLanguage;

    @BeforeEach
    void setUp() {
        languageRepository = new LanguageRepository();
        testLanguage = new Language(TEST_LANGUAGE);
        languageRepository.create(testLanguage);
    }

    @AfterEach
    void tearDown() {
        languageRepository.delete(testLanguage);
    }

    @Test
    void getAll_shouldReturnMoreLanguagesThanZero() {
        long count = languageRepository.getAll().size();
        assertTrue(count > 0);
    }

    @Test
    void getById_shouldReturnCorrectLanguage() {
        Language language = null;
        if (languageRepository.get(1L).isPresent()) {
            language = languageRepository.get(1L).get();
        }

        assertNotNull(language);
        assertEquals(1L, language.getId());
        System.out.println(language.getName());
        assertEquals("English", language.getName());
    }

    @Test
    void findByName_shouldReturnCorrectLanguage() {
        Language pattern = new Language(TEST_LANGUAGE);
        Stream<Language> languageStream = languageRepository.find(pattern);
        assertEquals(testLanguage, languageStream.findFirst().orElseThrow());
    }

    @Test
    void create_shouldCreateCorrectLanguage() {
        Long testLanguageId = testLanguage.getId();

        Language actualLanguage = null;
        if (languageRepository.get(testLanguageId).isPresent()) {
            actualLanguage = languageRepository.get(testLanguageId).get();
        }

        assertNotNull(actualLanguage);
        assertEquals(testLanguage, actualLanguage);
    }

    @Test
    void update_shouldUpdateSpecificLanguage() {
        Long testLanguageId = testLanguage.getId();

        testLanguage.setName(ANOTHER_TEST_LANGUAGE);
        languageRepository.update(testLanguage);

        Language actualLanguage = null;
        if (languageRepository.get(testLanguageId).isPresent()) {
            actualLanguage = languageRepository.get(testLanguageId).get();
        }

        assertNotNull(actualLanguage);
        assertEquals(ANOTHER_TEST_LANGUAGE, actualLanguage.getName());
    }

    @Test
    void delete_shouldDeleteSpecificLanguage() {
        Long testLanguageId = testLanguage.getId();

        Language languageBeforeDeletion = null;
        if (languageRepository.get(testLanguageId).isPresent()) {
            languageBeforeDeletion = languageRepository.get(testLanguageId).get();
        }
        assertNotNull(languageBeforeDeletion);

        languageRepository.delete(testLanguage);
        assertThrows(DaoException.class, () -> languageRepository.get(testLanguageId).get());
    }

}