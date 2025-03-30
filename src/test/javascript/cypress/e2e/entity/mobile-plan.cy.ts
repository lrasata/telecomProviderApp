import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('MobilePlan e2e test', () => {
  const mobilePlanPageUrl = '/mobile-plan';
  const mobilePlanPageUrlPattern = new RegExp('/mobile-plan(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const mobilePlanSample = { name: 'disrespect meaningfully', internetDataInGB: 3311, unlimitedSmsAndCalls: true };

  let mobilePlan;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/mobile-plans+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/mobile-plans').as('postEntityRequest');
    cy.intercept('DELETE', '/api/mobile-plans/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (mobilePlan) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/mobile-plans/${mobilePlan.id}`,
      }).then(() => {
        mobilePlan = undefined;
      });
    }
  });

  it('MobilePlans menu should load MobilePlans page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('mobile-plan');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MobilePlan').should('exist');
    cy.url().should('match', mobilePlanPageUrlPattern);
  });

  describe('MobilePlan page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(mobilePlanPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MobilePlan page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/mobile-plan/new$'));
        cy.getEntityCreateUpdateHeading('MobilePlan');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', mobilePlanPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/mobile-plans',
          body: mobilePlanSample,
        }).then(({ body }) => {
          mobilePlan = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/mobile-plans+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/mobile-plans?page=0&size=20>; rel="last",<http://localhost/api/mobile-plans?page=0&size=20>; rel="first"',
              },
              body: [mobilePlan],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(mobilePlanPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MobilePlan page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('mobilePlan');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', mobilePlanPageUrlPattern);
      });

      it('edit button click should load edit MobilePlan page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MobilePlan');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', mobilePlanPageUrlPattern);
      });

      it('edit button click should load edit MobilePlan page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MobilePlan');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', mobilePlanPageUrlPattern);
      });

      it('last delete button click should delete instance of MobilePlan', () => {
        cy.intercept('GET', '/api/mobile-plans/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('mobilePlan').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', mobilePlanPageUrlPattern);

        mobilePlan = undefined;
      });
    });
  });

  describe('new MobilePlan page', () => {
    beforeEach(() => {
      cy.visit(`${mobilePlanPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MobilePlan');
    });

    it('should create an instance of MobilePlan', () => {
      cy.get(`[data-cy="name"]`).type('founder worthwhile');
      cy.get(`[data-cy="name"]`).should('have.value', 'founder worthwhile');

      cy.get(`[data-cy="internetDataInGB"]`).type('2581');
      cy.get(`[data-cy="internetDataInGB"]`).should('have.value', '2581');

      cy.get(`[data-cy="unlimitedSmsAndCalls"]`).should('not.be.checked');
      cy.get(`[data-cy="unlimitedSmsAndCalls"]`).click();
      cy.get(`[data-cy="unlimitedSmsAndCalls"]`).should('be.checked');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        mobilePlan = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', mobilePlanPageUrlPattern);
    });
  });
});
