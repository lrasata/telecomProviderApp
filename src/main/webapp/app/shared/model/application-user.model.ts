import { IUser } from 'app/shared/model/user.model';
import { IMobilePlan } from 'app/shared/model/mobile-plan.model';

export interface IApplicationUser {
  id?: number;
  phoneNumber?: number;
  wallet?: number;
  internalUser?: IUser | null;
  chosenMobilePlan?: IMobilePlan | null;
}

export const defaultValue: Readonly<IApplicationUser> = {};
