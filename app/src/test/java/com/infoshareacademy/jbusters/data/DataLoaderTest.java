package com.infoshareacademy.jbusters.data;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DataLoaderTest {

    @Test
    void createTransactionList() {

        // given


        DataLoader dataLoader = Mockito.spy(new DataLoader());
        StaticFields staticFields = Mockito.spy(new StaticFields());
        int decimalPlaces = 2;

        doReturn(decimalPlaces).when(staticFields).getDecimalPlaces();

        List<String> transactionList = new ArrayList<>();
        String transaction =  "2017 09 26, Gdynia, Wzgórze Św. Maksymiliana, Śniadeckich, RYNEK WTÓRNY, 340000, 76, 5582, 3, BRAK MP, PRZECIĘTNY, 60-70, 1";
        transactionList.add(transaction);

        // when
        List<Transaction> result = dataLoader.createTransactionList(transactionList, false);

        // then
        assertThat(result).hasSize(1);
    }
}