
package com.example.sepa.collection.repository;

import com.example.sepa.collection.domain.DirectDebitCollection;

public interface CollectionRepository {

    void save(DirectDebitCollection collection);
}