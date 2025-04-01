export interface IMobilePlan {
  id?: number;
  name?: string;
  internetDataInGB?: number;
  unlimitedSmsAndCalls?: boolean;
  price?: number;
  description?: string | null;
}

export const defaultValue: Readonly<IMobilePlan> = {
  unlimitedSmsAndCalls: false,
};
