import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { SimulationStateModel, ConnectionModel }
  from '../models/simulation-state/simulation-state-module';

@Injectable({
  providedIn: 'root'
})
export class SimulationService {

  // =====================
  // STATE
  // =====================
  private state: SimulationStateModel = {
    machines: [],
    queues: [],
    connections: [],
    isRunning: false,
    time: 0
  };

  private stateSubject =
    new BehaviorSubject<SimulationStateModel>(this.state);

  simulationState$: Observable<SimulationStateModel> =
    this.stateSubject.asObservable();

  private machineId = 1;
  private queueId = 1;

  // used for click → click connection
  private selectedNode:
    { id: number; type: 'MACHINE' | 'QUEUE' } | null = null;

  constructor() { }

  // =====================
  // ADD ELEMENTS
  // =====================

  addMachine() {
    this.state.machines.push({
      id: this.machineId,
      name: `M${this.machineId}`,
      status: 'IDLE',
      position: { x: 100, y: 100 }   // 🔴 NEW
    });
    this.machineId++;
    this.emit();
  }

  addQueue() {
    this.state.queues.push({
      id: this.queueId,
      name: `Q${this.queueId}`,
      products: [],
      position: { x: 200, y: 200 }   // 🔴 NEW
    });
    this.queueId++;
    this.emit();
  }

  // =====================
  // DRAG POSITION UPDATE
  // =====================
  updatePosition(
    type: 'MACHINE' | 'QUEUE',
    id: number,
    x: number,
    y: number
  ) {
    const list =
      type === 'MACHINE'
        ? this.state.machines
        : this.state.queues;

    const node = list.find(n => n.id === id);
    if (node) {
      node.position = { x, y };
      this.emit();
    }
  }

  // =====================
  // CONNECTION CONTROL
  // =====================
  selectNode(id: number, type: 'MACHINE' | 'QUEUE') {

    // first click
    if (!this.selectedNode) {
      this.selectedNode = { id, type };
      return;
    }

    // second click
    const from = this.selectedNode;
    const to = { id, type };

    // allow Queue → Machine or Machine → Queue
    if (from.type !== to.type) {
      this.state.connections.push({
        fromId: from.id,
        fromType: from.type,
        toId: to.id,
        toType: to.type
      });
    }

    this.selectedNode = null;
    this.emit();
  }

  // =====================
  // CONTROL PANEL ACTIONS
  // =====================

  start() {
    this.updateState({ isRunning: true });
  }

  stop() {
    this.updateState({ isRunning: false });
  }

  reset() {
    this.state = {
      machines: [],
      queues: [],
      connections: [],   // 🔴 RESET LINES
      isRunning: false,
      time: 0
    };
    this.machineId = 1;
    this.queueId = 1;
    this.emit();
  }

  replay() {
    this.updateState({ isRunning: true });
  }

  // =====================
  // DELETE
  // =====================
  removeMachine(id: number) {
    this.state.machines =
      this.state.machines.filter(m => m.id !== id);

    this.state.connections =
      this.state.connections.filter(
        c => c.fromId !== id && c.toId !== id
      );

    this.emit();
  }

  removeQueue(id: number) {
    this.state.queues =
      this.state.queues.filter(q => q.id !== id);

    this.state.connections =
      this.state.connections.filter(
        c => c.fromId !== id && c.toId !== id
      );

    this.emit();
  }

  // =====================
  // HELPERS
  // =====================
  private emit() {
    this.stateSubject.next({ ...this.state });
  }

  private updateState(partial: Partial<SimulationStateModel>) {
    this.state = { ...this.state, ...partial };
    this.emit();
  }
}
