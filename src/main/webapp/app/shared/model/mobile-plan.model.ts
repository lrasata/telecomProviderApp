export interface IMobilePlan {
  id?: number;
  name?: string;
  internetDataInGB?: number;
  unlimitedSmsAndCalls?: boolean;
}

export const defaultValue: Readonly<IMobilePlan> = {
  unlimitedSmsAndCalls: false,
};
