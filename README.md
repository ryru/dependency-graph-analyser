# Gradle Module Dependency Graph

0. Find all *.gradle.kts file in folder and subfolder
1. Use the name of the parent folder as the moduel name
2. Search for dependency block
3. Find each line containing 'project'
4. Use the project value as the project-name
5. Use the first word as comment
6. Print module-name --> project-name: dependency-type

   ```
   stateDiagram-v2
       client --> core: api
       client_app --> client: implementation
   ```
Example: https://mermaid.live/edit#pako:eNpNj7EOwjAMRH8l8twujBmYWJnYUCRkJQYsmjhKXSRU9d8x7VA83fnZsm-GKInAw6iodGJ8NMz9-xCKs4oDU1HX90cXpZF3WPmf3LDWja7WO851oGwSlaVAB5laRk52YP4tBtCn8QDeZML2ChDKYnM4qVw-JYLXNlEHU037P-DvOIzWrViuIrunxCrtvCVYgyxfcMtGWg
