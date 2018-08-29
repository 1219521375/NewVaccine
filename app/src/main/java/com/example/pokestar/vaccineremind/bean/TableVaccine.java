package com.example.pokestar.vaccineremind.bean;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

/**
 * Created by PokeStar on 2018/8/21.
 */

@SmartTable(name="一类疫苗")
public class TableVaccine {
    public TableVaccine(String babyAge,String vacName,int vacTimes){
        this.babyAge = babyAge;
        this.vacName = vacName;
        this.vacTimes = vacTimes;
    }

    @SmartColumn(id = 1,name = "月龄")
    private String babyAge;
    @SmartColumn(id = 2,name = "疫苗")
    private String vacName;
    @SmartColumn(id = 3,name = "剂次")
    private int vacTimes;

}
