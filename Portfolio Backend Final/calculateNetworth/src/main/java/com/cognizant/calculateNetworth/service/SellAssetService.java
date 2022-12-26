package com.cognizant.calculateNetworth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognizant.calculateNetworth.controller.AuthClient;
import com.cognizant.calculateNetworth.model.Asset;
import com.cognizant.calculateNetworth.model.AuthResponse;
import com.cognizant.calculateNetworth.reposotory.AssetRepository;
@Service
public class SellAssetService {
	
	@Autowired
	private AssetRepository repository;
	
	@Autowired
	private AuthClient authClient;
	
	public void deleteStockAssetWithUnits(int portfolioId,List<String> idList) {
		for(String id:idList) {
		Asset a = repository.findByPortfolioidAndAssetidAndType(portfolioId,id,"Share");

			repository.delete(a);
//		}
		}
	}
	
	public void deleteMutualFundAssetWithUnits(int portfolioId,List<String> mfIdList) {
		for(String id:mfIdList) {
			Asset a = repository.findByPortfolioidAndAssetidAndType(portfolioId,id,"MF");

				repository.delete(a);
//			}
			}
	}
	
	public Boolean isSessionValid(String token) {
		try {
			AuthResponse authResponse = authClient.getValidity(token);
		} catch (Exception e) {
			return false;
		} 
		return true;
	}
	

}
