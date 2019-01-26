package com.infoshareacademy.jbusters.data;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class CalculatePriceTest {


    @Test
    public void testUpdatePrice() {
        // given

        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction();
        trans.setCity("Gdynia");
        trans.setDistrict("Witomino");
        trans.setTransactionDate(LocalDate.of(2018, 6, 20));
        trans.setStreet("Dabrowkowska");
        trans.setTypeOfMarket("RYNEK WTÓRNY");
        trans.setPrice(BigDecimal.ZERO);
        trans.setFlatArea(BigDecimal.valueOf(45));
        trans.setPricePerM2(BigDecimal.valueOf(5500));
        trans.setLevel(3);
        trans.setParkingSpot(ParkingPlace.BRAK_MP.getName());
        trans.setStandardLevel(StandardLevel.DOBRY.getName());
        trans.setConstructionYear("1980");
        trans.setConstructionYearCategory(2);

        transactions.add(trans);

        CalculatePrice test = Mockito.spy(new CalculatePrice());
        test.setFilteredList(transactions);
        test.setUserTransaction(trans);

        doReturn(transactions).when(test).getListToCalculateTrend(transactions);

        doReturn(BigDecimal.valueOf(0)).when(test).overallTrend(transactions);

        // when
        List<Transaction> result = test.updatePricesInList();

        //then
        assertThat(result.get(0).getPrice()).isEqualTo(BigDecimal.valueOf(0));
        assertThat(result.get(0).getFlatArea()).isEqualTo(BigDecimal.valueOf(45));
        assertThat(result.get(0).getPricePerM2()).isEqualTo(BigDecimal.valueOf(5500.00).setScale(2, RoundingMode.HALF_UP));

    }
}