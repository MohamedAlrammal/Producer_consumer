import { NodePosition } from '../node-position/node-position-module';
import { ProductModel } from '../product/product-module';

export interface QueueModel {
  id: number;
  name: string;
  position: NodePosition;
  products: ProductModel[];
}
