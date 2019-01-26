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

    private static final double TREND_PER_YEAR_2 = 0.002;


    @Test
    public void shouldReturnSamePrice_whenOverallTrendIs_0() {
        // given

        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction();
        trans.setTransactionDate(LocalDate.of(2018, 6, 20));
        trans.setPricePerM2(BigDecimal.valueOf(5500));

        transactions.add(trans);

        CalculatePrice test = Mockito.spy(new CalculatePrice());
        test.setFilteredList(transactions);
        test.setUserTransaction(trans);

        doReturn(transactions).when(test).getListToCalculateTrend(transactions);

        doReturn(BigDecimal.valueOf(0)).when(test).overallTrend(transactions);

        // when
        List<Transaction> result = test.updatePricesInList();

        //then
        assertThat(result.get(0).getPricePerM2()).isEqualTo(BigDecimal.valueOf(5500.00).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    public void shouldReturnCorrectValue_whenTrendIsNot_0() {
        // given

        List<Transaction> transactions = new ArrayList<>();
        Transaction trans = new Transaction();
        trans.setTransactionDate(LocalDate.now().minusDays(365));
        trans.setPricePerM2(BigDecimal.valueOf(5500));

        transactions.add(trans);

        CalculatePrice test = Mockito.spy(new CalculatePrice());
        test.setFilteredList(transactions);
        test.setUserTransaction(trans);

        doReturn(transactions).when(test).getListToCalculateTrend(transactions);

        doReturn(BigDecimal.valueOf(TREND_PER_YEAR_2)).when(test).overallTrend(transactions);

        // when
        List<Transaction> result = test.updatePricesInList();

        //then
        assertThat(result.get(0).getPricePerM2()).isEqualTo(BigDecimal.valueOf(9515.00).setScale(2, RoundingMode.HALF_UP));
    }
}