package com.cognizant.calculateNetworth.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.calculateNetworth.model.Asset;
import com.cognizant.calculateNetworth.model.MutualFundDetails;
import com.cognizant.calculateNetworth.model.SellObject;
import com.cognizant.calculateNetworth.model.StockDetails;
import com.cognizant.calculateNetworth.service.AssetService;
import com.cognizant.calculateNetworth.service.SellAssetService;

@RestController
@CrossOrigin("*")
@RequestMapping("/NetWorth")
public class StocksContoller {

	@Autowired
	private ShareDetailsFiegnProxy proxy;

	@Autowired
	private MutualFundDetailsFeignProxy mutualFundProxy;

	@Autowired
	private AssetService assetservice;

	@Autowired
	private SellAssetService sellService;
	
	
	@GetMapping("/calculateNetworth/{id}")
	public double getAsset(@RequestHeader("Authorization") String token,@PathVariable(value = "id") int id) 
	{
		double netWorth = 0.0;
		if(sellService.isSessionValid(token)) {
		List<String> stockAssetList = new ArrayList<>();
		List<String> mutualFundAssetList = new ArrayList<>();
		List<Double> stockValueList = new ArrayList<>();
		List<Double> mutualFundValueList = new ArrayList<>();
		List<Asset> assets = assetservice.getAllAssetForPortfolio(id);
		for (Asset a : assets) {
			if (a.getType().equals("Share")) {
				stockAssetList.add(a.getAssetid());
			} else {
				mutualFundAssetList.add(a.getAssetid());
			}
		}
		if (!stockAssetList.isEmpty()) {
			stockValueList = proxy.finddailyShareById(token,stockAssetList);
		}
		if (!mutualFundAssetList.isEmpty()) {
			mutualFundValueList = mutualFundProxy.getMutualDetailsById(token,mutualFundAssetList);
		}
		int stockCounter = 0, mfCounter = 0;
		for (Asset a : assets) {
			if (a.getType().equals("Share")) {
				netWorth += a.getUnits() * stockValueList.get(stockCounter);
				stockCounter++;
			} else {
				netWorth += a.getUnits() * mutualFundValueList.get(mfCounter);
				mfCounter++;
			}
		}
		}
		return netWorth;

	}

	@PostMapping("/Sell/Assets")
	public void calculateBalancePostSellPerStock(@RequestHeader("Authorization") String token,@RequestBody SellObject sell) {
		if(sellService.isSessionValid(token)) {
		List<String> stockIdList = sell.getStockIdList();
		List<String> mfIdList = sell.getMfAssetList();
		if (!stockIdList.isEmpty()) {
			sellService.deleteStockAssetWithUnits(sell.getPid(), stockIdList);
		}
		if (!mfIdList.isEmpty()) {
			sellService.deleteMutualFundAssetWithUnits(sell.getPid(), mfIdList);
		}
		}
		
	}
 
	@GetMapping("/GetAllAssets/{portfolioId}")
	public List<Asset> getAllAssets(@RequestHeader("Authorization") String token,@PathVariable(value = "portfolioId") int portfolioId) {
		if(sellService.isSessionValid(token)) {
			return assetservice.getAllAssetForPortfolio(portfolioId);
		}
		return null;
	}
	
}
