import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IEmailcredentials } from '../emailcredentials.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../emailcredentials.test-samples';

import { EmailcredentialsService } from './emailcredentials.service';

const requireRestSample: IEmailcredentials = {
  ...sampleWithRequiredData,
};

describe('Emailcredentials Service', () => {
  let service: EmailcredentialsService;
  let httpMock: HttpTestingController;
  let expectedResult: IEmailcredentials | IEmailcredentials[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(EmailcredentialsService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Emailcredentials', () => {
      const emailcredentials = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(emailcredentials).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Emailcredentials', () => {
      const emailcredentials = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(emailcredentials).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Emailcredentials', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Emailcredentials', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Emailcredentials', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addEmailcredentialsToCollectionIfMissing', () => {
      it('should add a Emailcredentials to an empty array', () => {
        const emailcredentials: IEmailcredentials = sampleWithRequiredData;
        expectedResult = service.addEmailcredentialsToCollectionIfMissing([], emailcredentials);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailcredentials);
      });

      it('should not add a Emailcredentials to an array that contains it', () => {
        const emailcredentials: IEmailcredentials = sampleWithRequiredData;
        const emailcredentialsCollection: IEmailcredentials[] = [
          {
            ...emailcredentials,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addEmailcredentialsToCollectionIfMissing(emailcredentialsCollection, emailcredentials);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Emailcredentials to an array that doesn't contain it", () => {
        const emailcredentials: IEmailcredentials = sampleWithRequiredData;
        const emailcredentialsCollection: IEmailcredentials[] = [sampleWithPartialData];
        expectedResult = service.addEmailcredentialsToCollectionIfMissing(emailcredentialsCollection, emailcredentials);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailcredentials);
      });

      it('should add only unique Emailcredentials to an array', () => {
        const emailcredentialsArray: IEmailcredentials[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const emailcredentialsCollection: IEmailcredentials[] = [sampleWithRequiredData];
        expectedResult = service.addEmailcredentialsToCollectionIfMissing(emailcredentialsCollection, ...emailcredentialsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const emailcredentials: IEmailcredentials = sampleWithRequiredData;
        const emailcredentials2: IEmailcredentials = sampleWithPartialData;
        expectedResult = service.addEmailcredentialsToCollectionIfMissing([], emailcredentials, emailcredentials2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(emailcredentials);
        expect(expectedResult).toContain(emailcredentials2);
      });

      it('should accept null and undefined values', () => {
        const emailcredentials: IEmailcredentials = sampleWithRequiredData;
        expectedResult = service.addEmailcredentialsToCollectionIfMissing([], null, emailcredentials, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(emailcredentials);
      });

      it('should return initial array if no Emailcredentials is added', () => {
        const emailcredentialsCollection: IEmailcredentials[] = [sampleWithRequiredData];
        expectedResult = service.addEmailcredentialsToCollectionIfMissing(emailcredentialsCollection, undefined, null);
        expect(expectedResult).toEqual(emailcredentialsCollection);
      });
    });

    describe('compareEmailcredentials', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareEmailcredentials(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareEmailcredentials(entity1, entity2);
        const compareResult2 = service.compareEmailcredentials(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareEmailcredentials(entity1, entity2);
        const compareResult2 = service.compareEmailcredentials(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareEmailcredentials(entity1, entity2);
        const compareResult2 = service.compareEmailcredentials(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
