package org.modular.cac;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modular.cac.endpoints.AttendanceController;
import org.modular.cac.endpoints.ExcelController;
import org.modular.cac.utils.AttendanceUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootTest
@ContextConfiguration(classes = {
        ExcelController.class
})
@Disabled
public class AppTest {

    @MockBean
    AttendanceController mockController;

    @Autowired
    ExcelController underTest;

    @Test
    public void testUpload() throws IOException {
        var file = Paths.get("src").resolve("main").resolve("resources").resolve("Lista de asistencia Basicos.xlsx");
        try(InputStream fileBytes = Files.newInputStream(file)){
            var result = underTest.processFile(fileBytes.readAllBytes());

            assertEquals(188,result.size());
        }
    }


    @Test
    void testDate(){
        var result = AttendanceUtils.getDateFrom(AttendanceUtils.parseMonth("Febrero"),20);
        assertEquals(20,result.getDayOfMonth());
        assertEquals(2,result.getMonth().getValue());
    }
}
