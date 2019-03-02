package com.google.android.gms.samples.vision.barcodereader.awsDb;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "nosqlrec-mobilehub-42069-items")
public class ItemsDo {

    private String _barcode;
    private Integer _typeId;

    public Integer get_typeId() {
        return _typeId;
    }

    @DynamoDBHashKey(attributeName = "barcode")
    @DynamoDBAttribute(attributeName = "barcode")
    public String get_barcode() {
        return _barcode;
    }

    public void set_barcode(String _barcode) {
        this._barcode = _barcode;
    }

    @DynamoDBHashKey(attributeName = "typeId")
    @DynamoDBAttribute(attributeName = "typeId")
    public void set_typeId(Integer _typeId) {
        this._typeId = _typeId;
    }
}
