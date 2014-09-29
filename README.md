![thumbnail](./images/uniVocity-api.png)

The uniVocity-API
====================

This project contains the public API of **[uniVocity](http://www.univocity.com/pages/about-univocity)**, a data integration framework for Java that abstracts away many of the complexities involved in ETL, data mappings and data synchronization processes.

Only uniVocity supports schema migration with referential integrity. Data updates can be detected and applied automatically. Migrated data can be updated easily using various data management strategies implemented by uniVocity.

The flexibility provided by the uniVocity API is unmatched. Unlike traditional ETL tools, you have control over every step of the process and are not dependent on pre-made connectors or data transformation functions. Simply define mappings from source to destination, and uniVocity will manage all data operations automatically.

Here's a quick example:

```java

	//Obtains the engine instance already configured
	DataIntegrationEngine engine = Univocity.getEngine(engineName);

	//Creates a mapping builder from "sourceDataStore" to "destinationDataStore".
	DataStoreMapping mapping = engine.map("sourceDataStore", "destinationDataStore");

	//With a data store mapping builder, we can start creating mappings between 
	//their entities (tables, files, custom entities you create yourself, etc)
	//This creates an entity mapping builder
	EntityMapping entityMapping = mapping.map("source_entity", "destination_entity");

	//With an entity mapping builder, we can create mappings between their fields. 
	//Queries, methods, external services and any object available in your 
	//application can be used as well.
	entityMapping.identity()
		.associate("source_key_1", "source_key_2")
		.toGeneratedId("destination_id");

	//Copies values from source_entity.some_description to destination_id.some_field. 
	//All values read from `some_description` and `another_description` will be
	//trimmed then converted to lower case, as specified in the sequence of functions
	entityMapping.value()
		.copy("some_description", "another_description")
		.to("some_field", "another_field")
		.readingWith("trim", "toLowerCase");

	//Creates a function that will execute calls to `yourAccountService` for some 
	//account number. The engine scope caches the result for each given account 
	//and reuses it while a mapping cycle is active.  
	engine.addFunction(EngineScope.CYCLE, "getAccountBalance", new FunctionCall<BigDecimal, String>() {
            @Override
            public BigDecimal execute(String accountNumber) {
                return yourAccountService.findAccount(accountNumber).getBalance();
            }
       });

	    //You can use values taken from the source entity to call your function
       entityMapping.value().copy("accountNumber").to("balance").readingWith("getAccountBalance");
       
       //Use it directly:
       entityMapping.value().copy("{getAccountBalance('12345')}").to("balance");
       
       //With variables:
       engine.setVariable("account", myAccountObject);
       entityMapping.value().copy("{getAccountBalance($account)}").to("balance");
              
	//Configures how data mapped from `source_entity` to 
	//`destination_entity` should be persisted, if at all.
	entityMapping.persistence()
		.usingMetadata()  //Generate metadata to enable features such as data change detection. 
		.deleteDisabled() //Do not remove any existing records
		.updateModified() //Update destination records if the counterpart in the source has been changed.
		.insertNewRows(); //Insert new rows identified in the source entity

	//Executes a data mapping cycle
	engine.executeCycle();
	
	...
```

The source code in this project is fully documented and you'll soon realize that uniVocity offers much more than any other ETL tool.

**[There is much, much more! Check our tutorial to learn all about uniVocity's unique features.](http://www.univocity.com/pages/univocity-tutorial)**
