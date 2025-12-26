import { SimulationStateModel } from '../simulation-state/simulation-state-module';

export interface SnapshotModel {
  timestamp: number;
  state: SimulationStateModel;
}
