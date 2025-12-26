import { NodePosition } from "../node-position/node-position-module";

export interface MachineModel {
  id: number;
  name: string;
  position: NodePosition;
  status: 'IDLE' | 'BUSY';
  currentProduct?: {
    color: string;
  };
}
