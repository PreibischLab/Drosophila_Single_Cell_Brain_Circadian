- get TopDrosLines 
mvn exec:java  -Dexec.mainClass=net.preibisch.flymapping.headless.TopDrosLines -Dexec.args="-n 5"

- get only ExpressedCellsInGenes 
mvn exec:java  -Dexec.mainClass=net.preibisch.flymapping.headless.ExpressedCellsInGenes

- get only PrepareDrosLine 
mvn exec:java  -Dexec.mainClass=net.preibisch.flymapping.headless.PrepareDrosLine

- get only Prepare_aerts_57k_cells_raw 
mvn exec:java  -Dexec.mainClass=net.preibisch.flymapping.headless.Prepare_aerts_57k_cells_raw