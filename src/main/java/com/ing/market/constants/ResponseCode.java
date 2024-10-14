package com.ing.market.constants;

import lombok.Getter;

@Getter
public enum ResponseCode {
    SUCCESS("Success"), SYSTEM_ERROR(
            "İşleminizi şimdi gerçekleştiremiyoruz, lütfen daha sonra tekrar deneyiniz."), E_INVALID_ORDER(
            "Sistem girmek istediğiniz emir tipini desteklememektedir."), E_ASSET_NOT_FOUND(
            "işlem yapmak için yeterli varlığınız bulunmamaktadır."), E_ORDER_NOT_FOUND(
            "İptal edilmek istenen emir bulunamadı."), E_ASSET_NOT_ENOUGH(
            "işlem yapmak için yeterli varlığınız bulunmamaktadır.");

    private final String message;

    ResponseCode(String message) {
        this.message = message;
    }
}
