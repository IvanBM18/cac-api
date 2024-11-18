package org.modular.cac.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RowData {
    private String lastName;
    private String firstName;
    private List<LocalDateTime> attendances = new ArrayList<>();

    public void addAttendance(LocalDateTime attendance){
        attendances.add(attendance);
    }

    @Override
    public String toString(){
        return lastName + firstName;
    }
}
