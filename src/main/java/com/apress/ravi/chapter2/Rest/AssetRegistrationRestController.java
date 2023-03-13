package com.apress.ravi.chapter2.Rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apress.ravi.chapter2.Exception.CustomErrorType;
import com.apress.ravi.chapter2.Sql.SqlServerConnect;
import com.apress.ravi.chapter2.dto.AssetsDTO;

/**
 * @author Ravi Kant Soni
 */
@RestController
@RequestMapping("/api/asset")
public class AssetRegistrationRestController {

	public static final Logger logger = LoggerFactory.getLogger(AssetRegistrationRestController.class);

	// private AssetJpaRepository assetJpaRepository;

	// @Autowired
	// public void setAssetJpaRepository(AssetJpaRepository assetJpaRepository) {
	// 	this.assetJpaRepository = assetJpaRepository;
	// }

	@GetMapping("/")
	public ResponseEntity<List<AssetsDTO>> listAllAssets() {
		logger.info("Fetching all assets");
		List<AssetsDTO> assets = SqlServerConnect.findAll();
		if (assets.isEmpty()) {
			return new ResponseEntity<List<AssetsDTO>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<AssetsDTO>>(assets, HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AssetsDTO> getAssetById(@PathVariable("id") final Long id) {
		logger.info("Fetching Asset with id {}", id);
		AssetsDTO asset = SqlServerConnect.findById(id);
		if (asset == null) {
			logger.error("asset with id {} not found.", id);
			return new ResponseEntity<AssetsDTO>(new CustomErrorType("Asset with id " + id + " not found"),
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AssetsDTO>(asset, HttpStatus.OK);
	}

	/**
	 * @exception MethodArgumentNotValidException
	 *                (validation fails)
	 */
	@PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetsDTO> createAsset(@RequestBody final AssetsDTO asset) {
		logger.info("Creating Asset : {}", asset);
		logger.info("Result of getName", asset.getName());
		if (SqlServerConnect.findByName(asset.getName()) != null) {
			logger.error("Unable to create. An asset with name {} already exist", asset.getName());
			return new ResponseEntity<AssetsDTO>(
					new CustomErrorType(
							"Unable to create new asset. An Asset with name " + asset.getName() + " already exist."),
					HttpStatus.CONFLICT);
		}
		SqlServerConnect.save(asset);
		return new ResponseEntity<AssetsDTO>(asset, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AssetsDTO> updateAsset(@PathVariable("id") final Long id, @RequestBody AssetsDTO asset) {
		logger.info("Updating Asset with id {}", id);
		AssetsDTO currentAsset = SqlServerConnect.findById(id);
		if (currentAsset == null) {
			logger.error("Unable to update. Asset with id {} not found.", id);
			return new ResponseEntity<AssetsDTO>(
					new CustomErrorType("Unable to upate. Asset with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		currentAsset.setName(asset.getName());
		currentAsset.setManufacturer(asset.getManufacturer());
		currentAsset.setPrice(asset.getPrice());
		SqlServerConnect.saveAndFlush(currentAsset);
		return new ResponseEntity<AssetsDTO>(currentAsset, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<AssetsDTO> deleteAsset(@PathVariable("id") final Long id) {
		logger.info("Deleting Asset with id {}", id);
		AssetsDTO asset = SqlServerConnect.findById(id);
		if (asset == null) {
			logger.error("Unable to delete. Asset with id {} not found.", id);
			return new ResponseEntity<AssetsDTO>(
					new CustomErrorType("Unable to delete. Asset with id " + id + " not found."), HttpStatus.NOT_FOUND);
		}
		SqlServerConnect.delete(id);
		return new ResponseEntity<AssetsDTO>(new CustomErrorType("Deleted Asset with id " + id + "."),
				HttpStatus.NO_CONTENT);
	}

}
