# Prodapt
Download the project and run :
test
sbt clean compile assembly
This will create an assembly jar which can be used inspark submit.

spark-submit  --master local[*]  --class Assignment   <jarpath>   <input folder> <output folder>
