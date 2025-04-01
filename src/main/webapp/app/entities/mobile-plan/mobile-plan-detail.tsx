import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mobile-plan.reducer';

export const MobilePlanDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mobilePlanEntity = useAppSelector(state => state.mobilePlan.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mobilePlanDetailsHeading">
          <Translate contentKey="telecomProviderApp.mobilePlan.detail.title">MobilePlan</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{mobilePlanEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="telecomProviderApp.mobilePlan.name">Name</Translate>
            </span>
          </dt>
          <dd>{mobilePlanEntity.name}</dd>
          <dt>
            <span id="internetDataInGB">
              <Translate contentKey="telecomProviderApp.mobilePlan.internetDataInGB">Internet Data In GB</Translate>
            </span>
          </dt>
          <dd>{mobilePlanEntity.internetDataInGB}</dd>
          <dt>
            <span id="unlimitedSmsAndCalls">
              <Translate contentKey="telecomProviderApp.mobilePlan.unlimitedSmsAndCalls">Unlimited Sms And Calls</Translate>
            </span>
          </dt>
          <dd>{mobilePlanEntity.unlimitedSmsAndCalls ? 'true' : 'false'}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="telecomProviderApp.mobilePlan.price">Price</Translate>
            </span>
          </dt>
          <dd>{mobilePlanEntity.price}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="telecomProviderApp.mobilePlan.description">Description</Translate>
            </span>
          </dt>
          <dd>{mobilePlanEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/mobile-plan" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/mobile-plan/${mobilePlanEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MobilePlanDetail;
