package com.google.android.gms.samples.vision.barcodereader.awsDb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class DynamoInteractions {

    private DynamoInteractions() {}

    public static void createItem(String barcode, Integer typeId, final DynamoDBMapper dynamoDBMapper) {
        final ItemsDo itemsDo = new ItemsDo();

        itemsDo.set_barcode(barcode);
        itemsDo.set_typeId(typeId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(itemsDo);
                // Item saved
            }
        }).start();
    }

    public static void createLocationMap(String municipality, Integer typeId, final DynamoDBMapper dynamoDBMapper) {
        final LocationMapDo locationMapDo = new LocationMapDo();

        locationMapDo.set_municipality(municipality);
        locationMapDo.set_typeId(typeId);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(locationMapDo);
                // Item saved
            }
        }).start();
    }

    public ItemsDo getItem(final String barcode, final DynamoDBMapper dynamoDBMapper) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<ItemsDo> callable = new Callable<ItemsDo>() {
            @Override
            public ItemsDo call() throws Exception {
                return dynamoDBMapper.load(ItemsDo.class, barcode);
            }
        };

        Future<ItemsDo> itemsDoFuture = executorService.submit(callable);
        executorService.shutdown();
        return itemsDoFuture.get();
    }

    public LocationMapDo getLocMap(final String loc, final DynamoDBMapper dynamoDBMapper) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Callable<LocationMapDo> callable = new Callable<LocationMapDo>() {
            @Override
            public LocationMapDo call() throws Exception {
                return dynamoDBMapper.load(LocationMapDo.class, loc);
            }
        };

        Future<LocationMapDo> locationMapDoFuture = executorService.submit(callable);
        executorService.shutdown();
        return locationMapDoFuture.get();
    }

}
