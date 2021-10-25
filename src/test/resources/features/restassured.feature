Feature: All CRUD operations related to http://dummy.restapiexample.com

  Scenario: Post
    Given A post request with the required values is made
      | Eugene | randNum | 26 |

  Scenario: Get all employees
    Given Get all employees request is made

  Scenario: Get specific employee
    Given Get employee with ID 5 request is made

  Scenario: Delete
    Given Delete request for ID 23 has been made
