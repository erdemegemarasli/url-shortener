from locust import HttpLocust, TaskSet, task, between

class UserBehaviour(TaskSet):
    def on_start(self):
        self.login()

    def login(self):
        self.client.post("/api/v1/authenticate",
        json={"userName":"TestUser","password":"123456789"})

    # @task(5)
    # def redirect(self):
    #     self.client.get("/r/ebc729")

    @task(1)
    def link_create(self):
        self.client.post("/api/v1/link",
        json={"url":"www.google.com","expTime":1823822831})

class WebsiteUser(HttpLocust):
    task_set = UserBehaviour
    wait_time = between(2,3)
