import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITool } from '../tool.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../tool.test-samples';

import { ToolService } from './tool.service';

const requireRestSample: ITool = {
  ...sampleWithRequiredData,
};

describe('Tool Service', () => {
  let service: ToolService;
  let httpMock: HttpTestingController;
  let expectedResult: ITool | ITool[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ToolService);
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

    it('should create a Tool', () => {
      const tool = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(tool).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Tool', () => {
      const tool = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(tool).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Tool', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Tool', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Tool', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addToolToCollectionIfMissing', () => {
      it('should add a Tool to an empty array', () => {
        const tool: ITool = sampleWithRequiredData;
        expectedResult = service.addToolToCollectionIfMissing([], tool);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tool);
      });

      it('should not add a Tool to an array that contains it', () => {
        const tool: ITool = sampleWithRequiredData;
        const toolCollection: ITool[] = [
          {
            ...tool,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addToolToCollectionIfMissing(toolCollection, tool);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Tool to an array that doesn't contain it", () => {
        const tool: ITool = sampleWithRequiredData;
        const toolCollection: ITool[] = [sampleWithPartialData];
        expectedResult = service.addToolToCollectionIfMissing(toolCollection, tool);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tool);
      });

      it('should add only unique Tool to an array', () => {
        const toolArray: ITool[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const toolCollection: ITool[] = [sampleWithRequiredData];
        expectedResult = service.addToolToCollectionIfMissing(toolCollection, ...toolArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tool: ITool = sampleWithRequiredData;
        const tool2: ITool = sampleWithPartialData;
        expectedResult = service.addToolToCollectionIfMissing([], tool, tool2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tool);
        expect(expectedResult).toContain(tool2);
      });

      it('should accept null and undefined values', () => {
        const tool: ITool = sampleWithRequiredData;
        expectedResult = service.addToolToCollectionIfMissing([], null, tool, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tool);
      });

      it('should return initial array if no Tool is added', () => {
        const toolCollection: ITool[] = [sampleWithRequiredData];
        expectedResult = service.addToolToCollectionIfMissing(toolCollection, undefined, null);
        expect(expectedResult).toEqual(toolCollection);
      });
    });

    describe('compareTool', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareTool(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareTool(entity1, entity2);
        const compareResult2 = service.compareTool(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareTool(entity1, entity2);
        const compareResult2 = service.compareTool(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareTool(entity1, entity2);
        const compareResult2 = service.compareTool(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
