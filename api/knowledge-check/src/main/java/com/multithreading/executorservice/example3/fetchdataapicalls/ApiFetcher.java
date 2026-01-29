package com.multithreading.executorservice.example3.fetchdataapicalls;

// TODO: Implement the ApiFetcher class, which implements Runnable
// The class should have a constructor that accepts the name of the API being fetched
// Implement the run() method to print a message indicating which thread is fetching data from the API

class ApiFetcher implements Runnable {

    // api name being fetched
    private String apiName;
    ApiFetcher(String apiName) {
        this.apiName = apiName;
    }

    @Override
    public void run() {
        System.out.println("Fetch data from: " + apiName + " by the thread name: " + Thread.currentThread().getName());
    }

}