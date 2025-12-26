import { ConnectionModel } from '../connection/connection-module';


export interface SimulationStateModel {
  machines: any[];
  queues: any[];
  connections: ConnectionModel[];
  isRunning: boolean;
  time: number;
}
export type { ConnectionModel };

