package com.oneangrybean.prototype.reactivespringintegration.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class CarrierObject<T> {
    private T value;
}
