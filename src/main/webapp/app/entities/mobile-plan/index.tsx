import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MobilePlan from './mobile-plan';
import MobilePlanDetail from './mobile-plan-detail';
import MobilePlanUpdate from './mobile-plan-update';
import MobilePlanDeleteDialog from './mobile-plan-delete-dialog';

const MobilePlanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MobilePlan />} />
    <Route path="new" element={<MobilePlanUpdate />} />
    <Route path=":id">
      <Route index element={<MobilePlanDetail />} />
      <Route path="edit" element={<MobilePlanUpdate />} />
      <Route path="delete" element={<MobilePlanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MobilePlanRoutes;
