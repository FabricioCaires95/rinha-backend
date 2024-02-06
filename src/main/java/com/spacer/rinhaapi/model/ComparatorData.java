package com.spacer.rinhaapi.model;

import java.util.Comparator;

public class ComparatorData implements Comparator<TransacaoResponse> {
    @Override
    public int compare(TransacaoResponse o1, TransacaoResponse o2) {
        if (o1.dataRealizacao().isAfter(o2.dataRealizacao())) {
            return -1;
        }

        if (o1.dataRealizacao().isBefore(o2.dataRealizacao())) {
            return 1;
        }

        return 0;
    }
}
