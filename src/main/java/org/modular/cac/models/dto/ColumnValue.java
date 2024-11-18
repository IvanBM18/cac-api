package org.modular.cac.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ColumnValue<T> {

    private T valueType;
}
