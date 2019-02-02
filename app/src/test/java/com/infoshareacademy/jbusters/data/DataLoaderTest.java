package com.infoshareacademy.jbusters.data;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class DataLoaderTest {

    @Test
    void createTransactionList_whenNotUserList() {

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
        assertThat(result.get(0).getCity()).isEqualTo("Gdynia");
    }

    @Test
    void createTransactionList_whenUserList() {

        // given

        DataLoader dataLoader = Mockito.spy(new DataLoader());
        StaticFields staticFields = Mockito.spy(new StaticFields());
        int decimalPlaces = 2;

        doReturn(decimalPlaces).when(staticFields).getDecimalPlaces();

        List<String> transactionListUser = new ArrayList<>();
        String transactionUser =  "2017 09 26, Gdynia, Wzgórze Św. Maksymiliana, Śniadeckich, RYNEK WTÓRNY, 340000, 76, 5582, 3, BRAK MP, PRZECIĘTNY, 60-70, 1, USER, true";
        transactionListUser.add(transactionUser);

        // when
        List<Transaction> resultUser = dataLoader.createTransactionList(transactionListUser, true);

        // then
        assertThat(resultUser).hasSize(1);
        assertThat(resultUser.get(0).getCity()).isEqualTo("Gdynia");
        assertThat(resultUser.get(0).isImportant()).isTrue();
    }
}