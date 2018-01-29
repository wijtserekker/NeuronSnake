import time

from han.controller.network.networkController import NetworkController

SLEEP = 0.1


class Control:

    def run(self):
        while self.running:
            start_time = time.time()
            self.network.update_network() # TEMPORARY
            # self.program.tick()
            end_time = time.time()
            tts = SLEEP - end_time + start_time
            if tts > 0:
                time.sleep(tts)
            else:
                print('Tick too long...')

    def __init__(self):
        self.network = NetworkController()

        self.program = None

        self.running = True

        self.run()


if __name__ == '__main__':
    Control()
