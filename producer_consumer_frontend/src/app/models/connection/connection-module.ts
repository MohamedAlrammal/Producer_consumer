export interface ConnectionModel {
  fromId: number;
  fromType: 'QUEUE' | 'MACHINE';
  toId: number;
  toType: 'QUEUE' | 'MACHINE';
}