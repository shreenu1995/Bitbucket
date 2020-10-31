package com.chatak.transit.afcs.server.dao;

import javax.validation.Valid;

import com.chatak.transit.afcs.server.dao.model.ProductManagement;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductSearchResponse;
import com.chatak.transit.afcs.server.pojo.web.ProductStatusChangeRequest;
import com.chatak.transit.afcs.server.pojo.web.ProductUpdateRequest;

public interface ProductDao {

	void saveProduct(ProductManagement productManagement);

	ProductSearchResponse searchProduct(ProductSearchRequest request, ProductSearchResponse response);

	ProductManagement updateProductStatus(@Valid ProductStatusChangeRequest request);

	boolean validateProductStatusUpdate(@Valid  ProductStatusChangeRequest request);

	boolean updateProductProfile(ProductUpdateRequest request);

}
